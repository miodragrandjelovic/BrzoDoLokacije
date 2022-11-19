using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services
{
    public class CommentLikeService : ICommentLikeService
    {
        private readonly ICommentLikeDAL _iCommentLikeDAL;
        
        public CommentLikeService(ICommentLikeDAL commentLikeDAL)
        {
            _iCommentLikeDAL = commentLikeDAL;
        }

        public Response AddLikeOnComment(CommentLike like)
        {
            try
            {
                bool succeed = _iCommentLikeDAL.AddLikeOnComment(like);
                return ResponseService.CreateOkResponse(succeed.ToString());
            }
            catch (Exception e)
            {
                return ResponseService.CreateErrorResponse(e.Message);
            }
        }

        public Response DeleteLikeFromComment(int likeID)
        {
            try
            {
                bool succeed = _iCommentLikeDAL.DeleteLikeFromComment(likeID);
                return ResponseService.CreateOkResponse(succeed.ToString());
            }
            catch (Exception e)
            {
                return ResponseService.CreateErrorResponse(e.Message);
            }
        }

        public List<CommentLike> GetLikesOfComment(int commentID)
        {
            return _iCommentLikeDAL.GetLikesOfComment(commentID);
        }

        public List<User> GetUsersWhoLiked(int commentID)
        {
            return _iCommentLikeDAL.GetUsersWhoLiked(commentID); 
        }

        public Response IsCommentLiked(int commentID, string username)
        {
            try
            {
                bool succeed = _iCommentLikeDAL.IsCommentLiked(commentID, username);
                return ResponseService.CreateOkResponse(succeed.ToString());
            }
            catch (Exception e)
            {
                return ResponseService.CreateErrorResponse(e.Message);
            }
        }
    }
}
