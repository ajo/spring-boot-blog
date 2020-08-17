# Spring Boot Blog

## About
This is a fork of [Reljicd's Demo Blog](https://github.com/reljicd/spring-boot-blog) that has been updated to
be more usable as a multi-user blog platform. 

## Summary
Every user has their own blog page, all blogs feed into one central blog (located on the index page). 
Non-authenticated users can see all blog posts, but cannot add new posts.

## Demo / Default Credentials
Admin
- username: **admin**
- password: **admin**

User 
- username: **user**
- password: **password**

[A demo](https://springblogdemo.herokuapp.com/) of the latest build is hosted on Heroku. The first load may take a 
moment as the Dyno sleeps when not in use.

## Updates From Forked Version
(This is just an overview Full details can be found in [issues tracker](https://github.com/ajo/spring-boot-blog/issues?q=is%3Aissue+is%3Aclosed))

- Dependencies
    - JDK 8 => JDK 11
    - Spring Boot 1.5.3 => Spring Boot 2.2.6
    - Spring Security 4 => Spring Security 5
    - Bootstrap 3.3.7 => Bootstrap 4.5.0
    - jQuery 2.1.4 => jQuery 3.5.1
     
- Functionality 
    - Removed easily abused comment system.
    - Overhauled visual styling.
    - Updated depreciated code.
    - Pages are now valid HTML.
    - Improved SEO
