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
            
            if(user == null)
                return false;

            if (post == null)
                return false;

            Like like = new Like()
            {
                PostId = postId,
                UserId = user.Id 
            };

            _context.Likes.Add(like);
            _context.SaveChanges(); 
            return true; 
        }

        public bool DeleteLike(int LikeId)
        {
            Like like = _context.Likes.Where(like => like.Id == LikeId).Include(like => like.User)
                                                                       .Include(like => like.Post)
                                                                       .FirstOrDefault();
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

            if (user == null)
                return false;

            if (post == null)
                return false;

            Like like = _context.Likes.Where(like => like.PostId == postId).FirstOrDefault();
            if (like == null)
                return false;

            return true; 
        }
    }
}
