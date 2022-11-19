using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL
{
    public class CommentDislikeDAL : ICommentDislikeDAL
    {
        private Database _context;
        private CommentDAL _commentDAL; 

        public CommentDislikeDAL(Database _context)
        {
            this._context = _context;
        }
        public bool AddDislikeOnComment(CommentDislike dislike)
        {
            if (dislike == null)
                throw new Exception(Constants.Constants.resNullValue);

            _context.CommentDislikes.Add(dislike);
            return true;
        }

        public bool DeleteDislikeFromComment(CommentDislike dislike)
        {
            if (dislike == null)
                throw new Exception(Constants.Constants.resNullValue);

            _context.CommentDislikes.Remove(dislike);
            return true;
        }

        public List<CommentDislike> GetDislikesOfComment(int commentID)
        {
            return _context.CommentDislikes.Where(dislike => dislike.CommentId == commentID).ToList();
        }

        public List<User> GetUsersWhoDisliked(int commentID)
        {
            return _context.CommentDislikes.Where(dislike => dislike.CommentId == commentID)
                                           .Include(dislike => dislike.User)
                                           .Select(dislike => dislike.User)
                                           .ToList();
        }

        public bool IsCommentDisliked(int commentID, string username)
        {
            CommentDislike dislike = _context.CommentDislikes.Where(dislike => (dislike.User.Username.Equals(username)) &&
                                                           (dislike.CommentId == commentID)).FirstOrDefault();

            if (dislike == null)
                return false;
            return true;
        }
    }
}
