using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL
{
    public class CommentLikeDAL : ICommentLikeDAL
    {
        private Database _context;
        private ICommentDAL _iCommentDAL;
        private IUserDAL _iUserDAL;

        public CommentLikeDAL(Database _context, ICommentDAL _iCommentDAL, IUserDAL _iUserDAL)
        {
            this._context = _context;
            this._iCommentDAL = _iCommentDAL;
            this._iUserDAL = _iUserDAL;
        }

        public bool AddLikeOnComment(CommentLike like)
        {
            if (like == null)
                throw new Exception(Constants.Constants.resNullValue);

            _context.CommentLikes.Add(like);
            return true; 
        }

        public bool DeleteLikeFromComment(int likeID)
        {
            CommentLike like = GetCommentLike(likeID);
            if (like == null)
                throw new Exception(Constants.Constants.resNullValue);

            _context.CommentLikes.Remove(like);
            return true;
        }

        public CommentLike GetCommentLike(int likeID)
        {
            return _context.CommentLikes.Where(like => like.Id == likeID).FirstOrDefault();
        }

        public List<CommentLike> GetLikesOfComment(int commentID)
        {
            return _context.CommentLikes.Where(like => like.CommentId == commentID).ToList(); 
        }

        public List<User> GetUsersWhoLiked(int commentID)
        {
            return _context.CommentLikes.Where(like => like.CommentId == commentID)
                                           .Include(like => like.User)
                                           .Select(like => like.User)
                                           .ToList();
        }

        public bool IsCommentLiked(int commentID, string username)
        {
            if (_iCommentDAL.GetComment(commentID) == null)
                throw new Exception(Constants.Constants.resNoFoundComment);

            if (_iUserDAL.GetUser(username) == null)
                throw new Exception(Constants.Constants.resNoFoundUser);

            CommentLike like = _context.CommentLikes.Where(like => (like.User.Username.Equals(username)) &&
                                                           (like.CommentId == commentID)).FirstOrDefault();

            if (like == null)
                return false;
            return true;
        }
    }
}
