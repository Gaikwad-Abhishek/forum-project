\documentclass{article}
\usepackage{graphicx} % Required for inserting images
\usepackage[a4paper, total={6in, 10in}]{geometry}

\title{ Forum Project \\ Edit-Delete Module And Like-Unlike Documentation }
\author{
  Abhishek Sanjay Gaikwad\\
  \texttt{abhishek.sg@prodapt.com}
  \and
  Brunda Marpadaga\\
  \texttt{marpadagabrunda.v@prodapt.com}
}
\date{September 2023}

\begin{document}

\maketitle

\section{Overview}

The forum project developed using spring boot framework allows a user to register, post content and add like or comment to a post. This documentation outlines the functionality for editing and deleting posts in a forum project. Users have the option to edit the content of their posts and delete them as needed.Users have the option to edit their comments, and both users and the owner of a post have the ability to delete comments. The owner of the post can delete any comment on their post.The functionality is built using CRUD (Create, Read, Update, Delete) operations, and custom queries are added to the repository to enable some complex operations.

\section{Accessing User Posts}

To access their own posts, users can visit the following URL:

\url{http://localhost:8080/forum/mypost}

\section{Editing a Post}

To edit a post, users should follow these steps:

\begin{enumerate}
    \item Log in to the forum project with their user account.
    \item Navigate to the "My Posts" section by visiting \url{http://localhost:8080/forum/mypost}.
    \item In the list of their posts, locate the post they want to edit.
    \item Click the "Edit" button associated with the post they wish to modify.
\end{enumerate}

\subsection{Editing Post Content}

After clicking the "Edit" button, users will be presented with a text area where they can update the content of the post. They can make their desired changes and then proceed to save the modifications.

\section{Deleting a Post}

To delete a post, users should follow these steps:

\begin{enumerate}
    \item Follow the First Three Steps of Editing a Post.
    \item Click the "Delete" button associated with the post they wish to remove.
\end{enumerate}



\subsection{Confirming Deletion}

Upon clicking the "Delete" button, a confirmation prompt should appear to ensure that they want to proceed with the deletion. Users should confirm the action, and the post will be permanently deleted.

\section{Unliking a post}
Upon clicking the button which initially reads "like" will post a like to the post, and will turn the button text to "dislike", which means the same button can be used to unlike the button.

\section{Editing a Comment}
After clicking the "Edit" button, you will be presented with a text area where you can update the content of the comment. Make your desired changes and then proceed to save the modifications.
\subsection{Deleting a Comment}
Note that the owner of the post has the right to delete any comment on their post, regardless of the comment's author.
Upon clicking the "Delete" button, the comment will be permanently removed.
\section{Implementation Details}

The functionality for editing and deleting posts is implemented using a CRUD repository. Additionally, custom queries are added to the repository to enable post updates.
\begin{figure}[h] % 'h' here means 'here,' you can use other placement options like 't' (top) or 'b' (bottom)
    \centering % Center the image horizontally
    \includegraphics[width=0.5\textwidth]{implementationflow.png} % Adjust the width as needed
    \caption{Implementation Flow}
\end{figure}
\subsection{Custom Queries}

Custom queries allow for efficient updating of post content. These queries should be implemented within the repository associated with posts.

\section{Security Considerations}

Ensure that only authorized users can access the "Edit" and "Delete" functionalities for their posts. Implement proper authentication and authorization mechanisms to prevent unauthorized access.

\section{Conclusion}

This documentation provides an overview of the "Edit-Delete Post" functionality in the forum project, including instructions for users on how to edit and delete their posts. It also highlights the implementation details, including the use of custom queries in the CRUD repository for post updates. Additionally, it emphasizes the importance of security considerations to protect user data and ensure proper access control.



\end{document}
