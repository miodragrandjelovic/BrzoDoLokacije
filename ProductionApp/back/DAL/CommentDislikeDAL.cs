using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL
{
    public class CommentDislikeDAL : ICommentDislikeDAL
    {
        private Database _context;
        private ICommentDAL _iCommentDAL;
        private IUserDAL _iUserDAL; 
        public CommentDislikeDAL(Database _context, ICommentDAL _iCommentDAL, IUserDAL _iUserDAL)
        {
            this._context = _context;
            this._iCommentDAL = _iCommentDAL;
            this._iUserDAL = _iUserDAL;
        }
        public bool AddDislikeOnComment(CommentDislike dislike)
        {
            if (dislike == null)
                throw new Exception(Constants.Constants.resNullValue); 
           
            _context.CommentDislikes.Add(dislike);
            _context.SaveChanges();
            return true;
        }

        public bool DeleteDislikeFromComment(string username, int commentID)
        {
            User user = _iUserDAL.GetUser(username);
            Comment comment = _iCommentDAL.GetComment(commentID); // nigde se ne koristi a poziva se

            if (user == null)
                throw new Exception(Constants.Constants.resNoFoundUser);

            if (_iCommentDAL.GetComment(commentID) == null) // moze samo comment ovaj gore 
                throw new Exception(Constants.Constants.resNoFoundComment);

            CommentDislike dislike = GetCommentDislike(username, commentID);
            _context.CommentDislikes.Remove(dislike);
            _context.SaveChanges();
            return true;
        }

        public CommentDislike GetCommentDislike(int dislikeID)
        {
            return _context.CommentDislikes.Where(dislike => dislike.Id == dislikeID).FirstOrDefault(); 
        }
        public bool CheckIfUserDislike(int userID, int commentID)
        {
            CommentDislike dislike = _context.CommentDislikes.Where(dislike => dislike.UserId.Equals(userID) && dislike.CommentId.Equals(commentID))
                                                  .FirstOrDefault();
            return dislike == null? false: true;
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

        public bool IsCommentDisliked(string username, int commentID)
        {
            if (_iCommentDAL.GetComment(commentID) == null)
                throw new Exception(Constants.Constants.resNoFoundComment);

            if (_iUserDAL.GetUser(username) == null)
                throw new Exception(Constants.Constants.resNoFoundUser); 

            CommentDislike dislike = _context.CommentDislikes.Where(dislike => (dislike.User.Username.Equals(username)) &&
                                                           (dislike.CommentId == commentID)).FirstOrDefault();

            if (dislike == null)
                return false;
            return true;
        }
        public CommentDislike GetCommentDislike(string username, int commentID)
        {
            CommentDislike dislike = _context.CommentDislikes.Where(dislike => (dislike.User.Username.Equals(username)) &&
                                                           (dislike.CommentId == commentID)).FirstOrDefault();
            return dislike;
        }

    }
}
