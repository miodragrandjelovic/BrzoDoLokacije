using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.UI.Interfaces;

namespace PyxisKapriBack.UI
{
    public class CommentUI : ICommentUI
    {
        private readonly ICommentService commentService;

        public CommentUI(ICommentService commentService)
        {
            this.commentService = commentService;
        }
        public Response AddComment(NewCommentDTO comment)
        {
            return commentService.AddComment(comment);
        }

        public Response DeleteComment(int commentId)
        {
            return commentService.DeleteComment(commentId);
        }

        public CommentDTO GetComment(int commentId)
        {
            var comment = commentService.GetComment(commentId);

            return new CommentDTO
            {
                Id = comment.Id,
                CommentText = comment.Text,
                DateOfCommenting = comment.DateCreated,
                Username = comment.User.Username,
                ProfileImage = Convert.ToBase64String(comment.User.ProfileImage)
            };
        }

        public List<CommentDTO> GetCommentsPost(int postId)
        {
            var comments = commentService.GetCommentsPost(postId);
            
            List<CommentDTO> commentsDTO = new List<CommentDTO>();   

            foreach (var comment in comments)
            {
                commentsDTO.Add(new CommentDTO
                {
                    Id = comment.Id,
                    CommentText = comment.Text,
                    DateOfCommenting = comment.DateCreated,
                    Username = comment.User.Username,
                    ProfileImage = Convert.ToBase64String(comment.User.ProfileImage)
                });
            }

            return commentsDTO;
        }
    }
}
