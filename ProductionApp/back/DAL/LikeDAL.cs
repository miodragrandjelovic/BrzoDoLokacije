using PyxisKapriBack.DAL.Interfaces;

namespace PyxisKapriBack.DAL
{
    public class LikeDAL : ILikeDAL
    {
        private Database _context; 
        private IUserDAL _iUserDAL;
        public LikeDAL(Database context, IUserDAL iUserDAL)
        {
            _context = context;
            _iUserDAL = iUserDAL;
        }
        public void AddLike(Post post, string username)
        {
            int userID = _iUserDAL.GetUser(username).Id;
            int postID = post.Id; 

            Like like = new Like()
            {
                PostId = postID,
                UserId = userID 
            };

            _context.Likes.Add(like);
            _context.SaveChanges(); 
        }

        public void DeleteLike(int LikeID)
        {
            Like like = _context.Likes.Where(like => like.Id == LikeID).FirstOrDefault(); 
            _context.Likes.Remove(like);
            _context.SaveChanges();
        }

        public List<Like> GetLikes(int PostID, out int NumberOfLikes)
        {
            List<Like> likes = _context.Likes.Where(like => like.PostId == PostID).Include(like => like.User).ToList(); 
            NumberOfLikes = likes.Count;
            return likes; 
        }

        public void UpdateLike(Like Like)
        {
            _context.Likes.Update(Like);
            _context.SaveChanges();
        }
    }
}
