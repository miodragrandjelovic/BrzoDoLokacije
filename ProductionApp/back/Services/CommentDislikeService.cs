using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services
{
    public class CommentDislikeService : ICommentDislikeService
    {
        private readonly ICommentDislikeDAL _iCommentDislikeDAL;
        private readonly IUserService userService;
        private readonly ICommentService commentService;
        
        public CommentDislikeService(ICommentDislikeDAL commentDislikeDAL, IUserService userService, ICommentService commentService)
        {
            _iCommentDislikeDAL = commentDislikeDAL;
            this.userService = userService; 
            this.commentService = commentService;
        }

        public Response AddDislikeOnComment(int commentID)
        {
            try
            {
                User user = userService.GetUser(userService.GetLoggedUser());
                Comment comment = commentService.GetComment(commentID);

                if (user == null)
                    throw new Exception(Constants.Constants.resNoFoundUser);
                if (comment == null)
                    throw new Exception(Constants.Constants.resNoFoundComment);

                CommentDislike dislike = new CommentDislike
                {
                    User = user,
                    Comment = comment
                }; 
                bool succeed = _iCommentDislikeDAL.AddDislikeOnComment(dislike); 
                return ResponseService.CreateOkResponse(succeed.ToString()); 
            }
            catch(Exception e)
            {
                return ResponseService.CreateErrorResponse(e.Message); 
            }
        }

        public Response DeleteDislikeFromComment(int commentID)
        {
            try
            {
                bool succeed = _iCommentDislikeDAL.DeleteDislikeFromComment(userService.GetLoggedUser(), commentID);
                return ResponseService.CreateOkResponse(succeed.ToString());
            }
            catch (Exception e)
            {
                return ResponseService.CreateErrorResponse(e.Message);
            }
        }

        public int GetCommentDislikeCount(int commentID)
        {
            return GetDislikesOfComment(commentID).Count();
        }

        public List<CommentDislike> GetDislikesOfComment(int commentID)
        {
            return _iCommentDislikeDAL.GetDislikesOfComment(commentID);
        }

        public List<User> GetUsersWhoDisliked(int commentID)
        {
            return _iCommentDislikeDAL.GetUsersWhoDisliked(commentID); 
        }

        public Response IsCommentDisliked(int commentID)
        {
            try
            {
                bool succeed = _iCommentDislikeDAL.IsCommentDisliked(userService.GetLoggedUser(), commentID);
                return ResponseService.CreateOkResponse(succeed.ToString());
            }
            catch (Exception e)
            {
                return ResponseService.CreateErrorResponse(e.Message);
            }
        }
    }
}
