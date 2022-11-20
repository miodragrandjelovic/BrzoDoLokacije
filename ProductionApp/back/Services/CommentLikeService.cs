using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services
{
    public class CommentLikeService : ICommentLikeService
    {
        private readonly ICommentLikeDAL _iCommentLikeDAL;
        private readonly IUserService userService;
        private readonly ICommentService commentService; 
        
        public CommentLikeService(ICommentLikeDAL commentLikeDAL, IUserService userService, ICommentService commentService)
        {
            _iCommentLikeDAL = commentLikeDAL;
            this.userService = userService;
            this.commentService = commentService;
        }

        public Response AddLikeOnComment(int commentID)
        {
            try
            {
                User user = userService.GetUser(userService.GetLoggedUser());
                Comment comment = commentService.GetComment(commentID);

                if (user == null)
                    throw new Exception(Constants.Constants.resNoFoundUser);
                if (comment == null)
                    throw new Exception(Constants.Constants.resNoFoundComment);

                CommentLike dislike = new CommentLike
                {
                    User = user,
                    Comment = comment
                };
                bool succeed = _iCommentLikeDAL.AddLikeOnComment(dislike);
                return ResponseService.CreateOkResponse(succeed.ToString());
            }
            catch (Exception e)
            {
                return ResponseService.CreateErrorResponse(e.Message);
            }
        }

        public Response DeleteLikeFromComment(int commentID)
        {
            try
            {
                bool succeed = _iCommentLikeDAL.DeleteLikeFromComment(userService.GetLoggedUser(), commentID);
                return ResponseService.CreateOkResponse(succeed.ToString());
            }
            catch (Exception e)
            {
                return ResponseService.CreateErrorResponse(e.Message);
            }
        }

        public int GetCommentLikeCount(int commentID)
        {
            return GetLikesOfComment(commentID).Count();
        }

        public List<CommentLike> GetLikesOfComment(int commentID)
        {
            return _iCommentLikeDAL.GetLikesOfComment(commentID);
        }

        public List<User> GetUsersWhoLiked(int commentID)
        {
            return _iCommentLikeDAL.GetUsersWhoLiked(commentID); 
        }

        public Response IsCommentLiked(int commentID)
        {
            try
            {
                bool succeed = _iCommentLikeDAL.IsCommentLiked(userService.GetLoggedUser(), commentID);
                return ResponseService.CreateOkResponse(succeed.ToString());
            }
            catch (Exception e)
            {
                return ResponseService.CreateErrorResponse(e.Message);
            }
        }
    }
}
