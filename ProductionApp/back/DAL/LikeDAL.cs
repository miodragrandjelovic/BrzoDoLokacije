using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.DAL
{
    public class LikeDAL : ILikeDAL
    {
        private Database _context; 
        private IUserDAL _iUserDAL;
        private IPostDAL _iPostDAL;
        public LikeDAL(Database context, IUserDAL iUserDAL, IPostDAL postDAL)
        {
            _context = context;
            _iUserDAL = iUserDAL;
            _iPostDAL = postDAL;
        }
        public bool AddLike(int postId, string username)
        {
            User user = _iUserDAL.GetUser(username);
            Post post = _iPostDAL.GetPost(postId);

            if ((user == null) || (post == null))
                return false;

            Like like = GetLike(postId, username);

            if (like != null)
                return false; 
            
            like = new Like()
            {
                PostId = postId,
                UserId = user.Id 
            };

            _context.Likes.Add(like);
            _context.SaveChanges(); 
            return true; 
        }

        public bool DeleteLike(int postId, string username)
        {
            User user = _iUserDAL.GetUser(username);
            Post post = _iPostDAL.GetPost(postId);

            if ((user == null) || (post == null))
                return false;

            Like like = GetLike(postId, username); 
            if (like == null)
                return false; 
            _context.Likes.Remove(like);
            _context.SaveChanges();
            return true; 
        }

        public List<Like> GetLikes(int PostId, out int NumberOfLikes)
        {
            List<Like> likes = _context.Likes.Where(like => like.PostId == PostId).Include(like => like.User)
                                                                                  .Include(like => like.Post)
                                                                                  .ToList(); 
            NumberOfLikes = likes.Count;
            return likes; 
        }

        public bool IsPostLiked(int postId, string username)
        {
            User user = _iUserDAL.GetUser(username);
            Post post = _iPostDAL.GetPost(postId);

            if ((user == null) || (post == null))
                return false;

            Like like = GetLike(postId, username); 
            if (like == null)
                return false;

            return true; 
        }
        public Like GetLike(int postId, string username)
        {
            Like like = _context.Likes.Where(like => (like.PostId == postId) && (like.User.Username.Equals(username)))
                                      .Include(like => like.User)
                                      .Include(like => like.Post)
                                      .FirstOrDefault();
            return like; 
        }

        public bool AddLike(Like like)
        {
            if (like.User == null)
                throw new Exception(Constants.Constants.resNullValue + " for user");
            if (like.Post == null)
                throw new Exception(Constants.Constants.resNullValue + " for post");

            _context.Likes.Add(like);
            _context.SaveChanges();
            return true; 
        }

        public bool UpdateLike(Like like)
        {
            if (like.User == null)
                throw new Exception(Constants.Constants.resNullValue + " for user");
            if (like.Post == null)
                throw new Exception(Constants.Constants.resNullValue + " for post");

            _context.Likes.Update(like);
            _context.SaveChanges();
            return true;
        }
    }
}
