using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL
{
    public class CommentLikeDAL : ICommentLikeDAL
    {
        private Database _context;

        public CommentLikeDAL(Database _context)
        {
            this._context = _context;
        }

        public bool AddLikeOnComment(CommentLike like)
        {
            if (like == null)
                throw new Exception(Constants.Constants.resNullValue);

            _context.CommentLikes.Add(like);
            return true; 
        }

        public bool DeleteLikeFromComment(CommentLike like)
        {
            if (like == null)
                throw new Exception(Constants.Constants.resNullValue);

            _context.CommentLikes.Remove(like);
            return true;
        }

        public List<CommentLike> GetLikesOfComment(int commentID)
        {
            return _context.CommentLikes.Where(like => like.CommentId == commentID).ToList(); 
        }

        public bool IsCommentLiked(int commentID, string username)
        {
            CommentLike like = _context.CommentLikes.Where(like => (like.User.Username.Equals(username)) && 
                                                           (like.CommentId == commentID)).FirstOrDefault();

            if (like == null)
                return false;
            return true; 
        }
    }
}
