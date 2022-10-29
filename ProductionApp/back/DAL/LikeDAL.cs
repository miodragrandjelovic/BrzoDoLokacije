using PyxisKapriBack.DAL.Interfaces;

namespace PyxisKapriBack.DAL
{
    public class LikeDAL : ILikeDAL
    {
        private Database _context; 
        public LikeDAL(Database context)
        {
            _context = context;
        }
        public void AddLike(Post post, User user)
        {
            Like like = new Like();
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
