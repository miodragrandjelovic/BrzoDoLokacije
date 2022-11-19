using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services
{
    public class CommentDislikeService : ICommentDislikeService
    {
        private readonly ICommentDislikeDAL _iCommentDislikeDAL;
        
        public CommentDislikeService(ICommentDislikeDAL commentDislikeDAL)
        {
            _iCommentDislikeDAL = commentDislikeDAL;
        }

        public Response AddDislikeOnComment(CommentDislike dislike)
        {
            try
            {
                bool succeed = _iCommentDislikeDAL.AddDislikeOnComment(dislike);
                return ResponseService.CreateOkResponse(succeed.ToString()); 
            }
            catch(Exception e)
            {
                return ResponseService.CreateErrorResponse(e.Message); 
            }
        }

        public Response DeleteDislikeFromComment(int dislikeID)
        {
            try
            {
                bool succeed = _iCommentDislikeDAL.DeleteDislikeFromComment(dislikeID);
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

        public Response IsCommentDisliked(int commentID, string username)
        {
            try
            {
                bool succeed = _iCommentDislikeDAL.IsCommentDisliked(commentID, username);
                return ResponseService.CreateOkResponse(succeed.ToString());
            }
            catch (Exception e)
            {
                return ResponseService.CreateErrorResponse(e.Message);
            }
        }
    }
}
