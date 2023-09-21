# Forum Project Edit-Delete Module and Like-Unlike Documentation

*Author: Abhishek Sanjay Gaikwad (abhishek.sg@prodapt.com)*
*Author: Brunda Marpadaga (marpadagabrunda.v@prodapt.com)*
*Date: September 2023*

## Overview

The forum project developed using the Spring Boot framework allows users to register, post content, and add likes or comments to a post. This documentation outlines the functionality for editing and deleting posts in a forum project. Users have the option to edit the content of their posts and delete them as needed. Users also have the option to edit their comments, and both users and the owner of a post have the ability to delete comments. The owner of the post can delete any comment on their post. The functionality is built using CRUD (Create, Read, Update, Delete) operations, and custom queries are added to the repository to enable some complex operations.

## Accessing User Posts

To access their own posts, users can visit the following URL:

[http://localhost:8080/forum/mypost](forum-project/main/implementationflow.png)

## Editing a Post

To edit a post, users should follow these steps:

1. Log in to the forum project with their user account.
2. Navigate to the "My Posts" section by visiting [http://localhost:8080/forum/mypost](http://localhost:8080/forum/mypost).
3. In the list of their posts, locate the post they want to edit.
4. Click the "Edit" button associated with the post they wish to modify.

### Editing Post Content

After clicking the "Edit" button, users will be presented with a text area where they can update the content of the post. They can make their desired changes and then proceed to save the modifications.

## Deleting a Post

To delete a post, users should follow these steps:

1. Follow the first three steps of editing a post.
2. Click the "Delete" button associated with the post they wish to remove.

### Confirming Deletion

Upon clicking the "Delete" button, a confirmation prompt should appear to ensure that they want to proceed with the deletion. Users should confirm the action, and the post will be permanently deleted.

## Unliking a Post

Upon clicking the button that initially reads "like," a like will be added to the post, and the button text will change to "dislike," which means the same button can be used to unlike the post.

## Editing a Comment

After clicking the "Edit" button, users will be presented with a text area where they can update the content of the comment. They can make their desired changes and then proceed to save the modifications.

### Deleting a Comment

Note that the owner of the post has the right to delete any comment on their post, regardless of the comment's author. Upon clicking the "Delete" button, the comment will be permanently removed.

## Implementation Details

The functionality for editing and deleting posts is implemented using a CRUD repository. Additionally, custom queries are added to the repository to enable post updates.

![Implementation Flow](https://github.com/Gaikwad-Abhishek/forum-project/blob/main/implementationflow.PNG)

### Custom Queries

Custom queries allow for efficient updating of post content. These queries should be implemented within the repository associated with posts.

## Security Considerations

Ensure that only authorized users can access the "Edit" and "Delete" functionalities for their posts. Implement proper authentication and authorization mechanisms to prevent unauthorized access.

## Conclusion

This documentation provides an overview of the "Edit-Delete Post" functionality in the forum project, including instructions for users on how to edit and delete their posts. It also highlights the implementation details, including the use of custom queries in the CRUD repository for post updates. Additionally, it emphasizes the importance of security considerations to protect user data and ensure proper access control.
