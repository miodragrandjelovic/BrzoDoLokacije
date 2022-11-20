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
        private readonly ICommentDislikeDAL commentDislikeDAL;

        public CommentLikeService(ICommentLikeDAL commentLikeDAL, IUserService userService, ICommentService commentService, ICommentDislikeDAL commentDislikeDAL)
        {
            _iCommentLikeDAL = commentLikeDAL;
            this.userService = userService;
            this.commentService = commentService;
            this.commentDislikeDAL = commentDislikeDAL;
        }

        public Response ChangeLikeStateOnComment(int commentID)
        {
            try
            {
                User user = userService.GetUser(userService.GetLoggedUser());
                Comment comment = commentService.GetComment(commentID);

                if (user == null)
                    throw new Exception(Constants.Constants.resNoFoundUser);
                if (comment == null)
                    throw new Exception(Constants.Constants.resNoFoundComment);
                if (!commentDislikeDAL.CheckIfUserDislike(user.Id, commentID))
                    throw new Exception(Constants.Constants.resNoFoundComment); // napravi u konstantama za forbiden
                if (_iCommentLikeDAL.IsCommentLiked(user.Username, commentID))
                {
                    var answer = _iCommentLikeDAL.DeleteLikeFromComment(user.Username, commentID);
                    if (answer)
                        return ResponseService.CreateOkResponse("OK");

                    return ResponseService.CreateErrorResponse("NOT OK");
                }
                else
                {
                    CommentLike like = new CommentLike
                    {
                        User = user,
                        Comment = comment
                    };
                    bool succeed = _iCommentLikeDAL.AddLikeOnComment(like);
                    return ResponseService.CreateOkResponse(succeed.ToString());

                }
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
