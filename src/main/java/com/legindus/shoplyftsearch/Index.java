package com.legindus.shoplyftsearch;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.KStemFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.document.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.*;
import org.apache.lucene.index.*;
import org.tartarus.snowball.ext.EnglishStemmer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Index {

    private Analyzer analyser;
    private Directory index;
    private IndexWriterConfig indexWriterConfig;
    private IndexWriter indexWriter;

    public IndexSearcher indexSearcher;

    public Index(List<CategoryDocument> documents)  {

        try {
            analyser = new Analyzer() {
                @Override
                protected TokenStreamComponents createComponents(String fieldName) {
                    StandardTokenizer src = new StandardTokenizer();
                    TokenStream result = new StandardFilter(src);
                    result = new LowerCaseFilter(result);
                    result = new StopFilter(result, EnglishAnalyzer.getDefaultStopSet());
                    result = new KStemFilter(result);
                    //result = new SnowballFilter(result, new EnglishStemmer());

                    return new TokenStreamComponents(src, result);
                }
            };
            index = new RAMDirectory();
            //index = FSDirectory.open(Paths.get("/Users/sanjay/Desktop/index"));
            indexWriterConfig = new IndexWriterConfig(analyser);
            indexWriter = new IndexWriter(index, indexWriterConfig);
            for (CategoryDocument document : documents) {
                Document doc = new Document();

                doc.add(new TextField("category", document.categoryName, Field.Store.YES));
                StringBuilder builder = new StringBuilder();
                builder.append(document.categoryName);
                for (String s : document.keywords) {
                    builder.append(" ");
                    builder.append(s);
                }
                doc.add(new TextField("keywords", builder.toString(), Field.Store.YES));
                indexWriter.addDocument(doc);
            }
            indexWriter.commit();
            IndexReader reader = DirectoryReader.open(index);
            for(int i = 0; i < reader.maxDoc(); ++i) {
                Document doc = reader.document(i);
                System.out.println("CONTENT " + doc.get("keywords"));
            }
            indexSearcher = new IndexSearcher(reader);
        } catch (IOException e) {
            System.err.println("Error indexing documents: " + e.getMessage());
        }
   }

    public List<String> search(String query) {
        QueryParser parser = new QueryParser("keywords", analyser);

        try {
            Query q = parser.parse(query);
            System.out.println(q);
            TopDocs hits = indexSearcher.search(q, 10000);
            ScoreDoc[] docs = hits.scoreDocs;
            System.out.println(hits.totalHits);
            ArrayList<String> list = new ArrayList<>();
            for(ScoreDoc doc: docs) {
                int docId = doc.doc;
                Document d = indexSearcher.doc(docId);
                list.add(d.get("category"));
            }

            return list;
        } catch (IOException e) {
            System.err.println("Search Error in Index::search(): " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("Search error in parsing query: " + e.getMessage());
        }

        return null;
    }

    public static void main(String[] args) {
        CategoryDocument[] docs = {
                new CategoryDocument("sanjay", Arrays.asList("sanjay", "is awesome", "and amazing", "and a really good coder", "intelligent")),
                new CategoryDocument("helen", Arrays.asList("helen", "is an great violinist", "and is really really smart"))
        };

        Index index = new Index(Arrays.asList(docs));
        System.out.println(index.search("who can code"));
    }

}
