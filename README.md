### Our team (Java Juice Team) developed backend part for the marketplace website - Ads Online.

# Main functionalities:

- User authorization and authentification.
- User roles separation: User / Admin.
- CRUD for the ads on website: Admin can edit / delete all adds while User only his own.
- All users can leave comments for the ads. 
- Search ads by title is available.
- There is option to add pictures for the ads. 

# Specification:

https://github.com/BizinMitya/front-react-avito/blob/v1.11/openapi.yaml

# Frontend image installation and run in Docker

- command to run the image for Windows / Linux:  
`docker run --rm --name front-react-avito-v1.11-instance -p3000:3000 ghcr.io/bizinmitya/front-react-avito:v1.11`

# Stack of technologies:
Java 17, Spring Boot 3.0.5, Hibernate, PostgreSQL, Lombok, Spring Doc Open Api   
JUnit, Mockito   
Docker  

# Java Juice Team:
- Moskalenko Alexey
- Tsvetov Denis
- Ishmaev Tamerlan
- Krivobokova Olga
