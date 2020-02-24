# Client Side Password Manager

## What will the application do?

- The application provides a client side database that can **store** and **manage** passwords
  that are provided by the user or create completely **novel** passwords for use by the user.

- The application will be able to generate passwords given specifications such as *length*,
  *valid characters*, etc. and the passwords will have a high degree of **entropy** and as such
  would be hard to **brute-force**.

- The application is accessed via a **master password** that will be encrypted and stored in
  a special database file relating to the program.

  - The text of the actual database file itself will be encrypted as well and can only 
    be decrypted by the program provided the master password provided matches the 
    encrypted master password located inside the database file.
    
- The database file stores all of the
  *relevant* information the program requires/uses and this database file can be moved
  and accessed on **any device** as long as this program is also present and the master 
  password is provided.

## Who will use it?

The application can be used by anyone that is looking for a **secure central database** to
store all of their passwords, or to **generate secure new passwords** for use. Ideally it
would be used for both. Of course I don't expect people to **actually** use it because
I am not an expert in cryptography and so this application probably won't be secure
enough for commercial use.

## Why is this project of interest to you?

I've always had an interest in **cryptography** and this project will allow me to explore and
hopefully learn a little about how passwords are effectively stored, created, and
graded from weak to strong. Additionally, I feel like this project will be **challenging**
enough to be a valuable **learning experience** and it is something that can always be
improved and worked on, so it can be as hard as I make it. Lastly, I have a feeling
that working on this is gonna be **fun**.

## User Stories

- As a user I want to be able to add an entry to my database file.
- As a user I want to be able to remove an entry to my database file.
- As a user I want to be able to view the password and username of my entries.
- As a user I want my password to be secure and encrypted.
- As a user I want to have to remember only one password for the entire database.
- As a user I want to be able to save and encrypt my database file and all of it's entries.
- As a user I want to be able to load my encrypted database along with all it's entries.