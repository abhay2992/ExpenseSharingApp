#Discussion

##Entities
- User: name, username, password, phone
- Expense: description, date
- Group: name

##Relationship
- User *-- Participates in --* Expense
- User *-- Member of --* Group
- User 1-- Admin --* Group
- Expense *-- Belongs to --0/1 Group


##Schema
User
id | name | phone | password

Expense
id | description

Group
id | owner