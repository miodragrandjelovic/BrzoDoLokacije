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
            _context.SaveChanges();
            return true;
        }

        public bool DeleteLikeFromComment(string username, int commentID)
        {
            User user = _iUserDAL.GetUser(username); 
            Comment comment = _iCommentDAL.GetComment(commentID);

            if (user == null)
                throw new Exception(Constants.Constants.resNoFoundUser);

            if (comment == null)
                throw new Exception(Constants.Constants.resNoFoundComment);

            CommentLike like = GetCommentLike(username, commentID);

            _context.CommentLikes.Remove(like);
            _context.SaveChanges();

            return true;
        }

        public CommentLike GetCommentLike(int likeID)
        {
            return _context.CommentLikes.Where(like => like.Id == likeID).FirstOrDefault();
        }
        public CommentLike GetCommentLike(string username, int commentID)
        {
            CommentLike like = _context.CommentLikes.Where(like => (like.User.Username.Equals(username)) &&
                                                           (like.CommentId == commentID)).FirstOrDefault();
            return like; 
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

        public bool IsCommentLiked(string username, int commentID)
        {
            if (_iCommentDAL.GetComment(commentID) == null)
                throw new Exception(Constants.Constants.resNoFoundComment);

            if (_iUserDAL.GetUser(username) == null)
                throw new Exception(Constants.Constants.resNoFoundUser);

            CommentLike like = GetCommentLike(username, commentID);

            if (like == null)
                return false;
            return true;
        }
    }
}
