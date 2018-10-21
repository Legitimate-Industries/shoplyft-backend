# ShopLyft


## Inspiration
Inspired by a background of retail experience with the goal of a streamlined customer experience in mind, ShopLyft is a flexible API that allows a customer to connect with experts in the department they are searching as soon as they walk into the store. By submitting a question as they walk into the store, ShopLyft allows customers to match with the best employees to help them find what they need. Not only will this help improve customer experience, but a feedback system also allows employees to gain insight as to how to be more productive and ensure customer satisfaction.

## Technical Challenges 
Where to start? While the majority of our application was powered by Firebase's real-time database, we still needed a server to handle the NLP portion, so we opted to host a RESTful web service using Java on a GCE instance. Despite having previous success with GCP's Application-Default Credentials, we were unable to get them to work with.
