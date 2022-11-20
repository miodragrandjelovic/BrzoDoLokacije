using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Services
{
    public class CommentService : ICommentService
    {
        private readonly ICommentDAL commentDAL;
        private readonly IPostService postService;
        private readonly IUserService userService;

        public CommentService(ICommentDAL commentDAL, IPostService postService,IUserService userService)
        {
            this.commentDAL = commentDAL;
            this.postService = postService;
            this.userService = userService;
        }
        public Response AddComment(NewCommentDTO comment)
        {
            var loggedUser = userService.GetUser(userService.GetLoggedUser());

            if (loggedUser == null)
                return new Response
                {
                    StatusCode = StatusCodes.Status404NotFound,
                    Message = "User not found"
                };
            var post = postService.GetPost(comment.PostId); 
            if(post == null)
                return new Response
                {
                    StatusCode = StatusCodes.Status404NotFound,
                    Message = "Post not found"
                };
            var newComment = new Comment
            {
                PostId = comment.PostId,
                Post = post,
                DateCreated = DateTime.Now,
                Text = comment.Comment,
                UserId = loggedUser.Id,
                User = loggedUser,
            };
            var succeed = commentDAL.AddComment(newComment);
            if (!succeed)
            {
                return new Response
                {
                    StatusCode = StatusCodes.Status500InternalServerError,
                    Message = "Error while adding comment!"
                };
            }

            return new Response
            {
                StatusCode = StatusCodes.Status200OK,
                Message = "Comment added succesffuly!"
            };
        }

        public Response DeleteComment(int commentId)
        {
            try
            {
                var succeed = commentDAL.DeleteComment(commentId);
                return new Response
                {
                    StatusCode = StatusCodes.Status500InternalServerError,
                    Message = "Error while deleting comment!"
                };
            }
            catch
            {
                return new Response
                {
                    StatusCode = StatusCodes.Status200OK,
                    Message = "Comment deleted succesffuly!"
                };
            }
        }

        public Comment GetComment(int commentId)
        {
            return commentDAL.GetComment(commentId);
        }

        public List<Comment> GetCommentsPost(int postId)
        {
            return commentDAL.GetCommentsPost(postId);  
        }
    }
}
