Classes used: com.springsecurity.springsecurityProject.User
              com.springsecurity.springsecurityProject.Role

There are two entities User and Role connected with many to many mapping that is 
a user can have multiple roles and a single role can belong to multiple users.
A user can have at most two roles: admin and user. User having both the roles can access
uri meant for user and admin both

Bootstrap.java creates three users when application is up and running

There are three users with following credentials:
User1;
username: pinki
password: pinki
role:     admin

User 2:
username: himanshu
password: himanshu
role:     user

User 3:
username: ankit
password: ankit
role:     admin,user

mappings are defined in:
com.springsecurity.springsecurityProject.SpringSecurityProjectApplication

http://localhost:8080/admin/home-> user having admin role can access this
http://localhost:8080/user/home->  user having user role can access this
http://localhost:8080/home->       both user and admin can access this

mappings with roles are defined here: 
com/springsecurity/springsecurityProject/ResourceServerConfiguration.java:41