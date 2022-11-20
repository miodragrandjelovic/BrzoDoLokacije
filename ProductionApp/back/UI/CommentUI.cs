using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.UI.Interfaces;

namespace PyxisKapriBack.UI
{
    public class CommentUI : ICommentUI
    {
        private readonly ICommentService commentService;
        private readonly ICommentDislikeService commentDislikeService;
        private readonly ICommentLikeService commentLikeService;
        private readonly IUserService userService; 

        public CommentUI(ICommentService commentService, ICommentLikeService commentLikeService, ICommentDislikeService commentDislikeService, IUserService userService)
        {
            this.commentService = commentService;
            this.commentLikeService = commentLikeService;
            this.commentDislikeService = commentDislikeService;
            this.userService = userService;
        }
        public Response AddComment(NewCommentDTO comment)
        {
            return commentService.AddComment(comment);
        }

        public Response ChangeDislikeStateOnComment(int commentID)
        {
            return commentDislikeService.ChangeDislikeStateOnComment(commentID); 
        }

        public Response ChangeLikeStateOnComment(int commentID)
        {

            return commentLikeService.ChangeLikeStateOnComment(commentID); 
        }

        public Response DeleteComment(int commentId)
        {
            return commentService.DeleteComment(commentId);
        }

        public Response DeleteDislike(int commentID)
        {
            return commentDislikeService.DeleteDislikeFromComment(commentID);
        }

        public Response DeleteLike(int commentID)
        {
            return commentLikeService.DeleteLikeFromComment(commentID);
        }

        public CommentDTO GetComment(int commentId)
        {
            var comment = commentService.GetComment(commentId);
            if (comment == null)
                return null; 
            return new CommentDTO
            {
                Id = comment.Id,
                CommentText = comment.Text,
                DateOfCommenting = comment.DateCreated.ToString(),
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
                    DateOfCommenting = comment.DateCreated.ToString(),
                    Username = comment.User.Username,
                    ProfileImage = Convert.ToBase64String(comment.User.ProfileImage),
                    LikeCount = commentLikeService.GetCommentLikeCount(comment.Id), 
                    DislikeCount = commentDislikeService.GetCommentDislikeCount(comment.Id)
                });
            }

            return commentsDTO;
        }

        public List<UserShortDTO> GetUsersWhoDisliked(int commentID)
        {
            var users = commentDislikeService.GetUsersWhoDisliked(commentID);

            var usersDTO = new List<UserShortDTO>();

            foreach (var user in users)
            {
                usersDTO.Add(new UserShortDTO
                {
                    ProfileImage = user.ProfileImage == null ? string.Empty : Convert.ToBase64String(user.ProfileImage),
                    Username = user.Username,
                    FirstName = user.FirstName,
                    LastName = user.LastName
                });
            }
            return usersDTO;
        }

        public List<UserShortDTO> GetUsersWhoLiked(int commentID)
        {
            var users = commentLikeService.GetUsersWhoLiked(commentID);

            var usersDTO = new List<UserShortDTO>();

            foreach (var user in users)
            {
                usersDTO.Add(new UserShortDTO
                {
                    ProfileImage = user.ProfileImage == null ? string.Empty : Convert.ToBase64String(user.ProfileImage),
                    Username = user.Username,
                    FirstName = user.FirstName,
                    LastName = user.LastName
                });
            }
            return usersDTO;
        }

        public Response IsCommentDisliked(int commentID)
        {
            return commentDislikeService.IsCommentDisliked(commentID); 
        }

        public Response IsCommentLiked(int commentID)
        {
            return commentLikeService.IsCommentLiked(commentID); 
        }
    }
}
