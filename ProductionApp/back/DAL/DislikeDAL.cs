using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL
{
    public class DislikeDAL : IDislikeDALcs
    {
        private Database _context; 
        public DislikeDAL(Database context)
        {
            _context = context;
        }

        public bool AddDislike(Dislike Dislike)
        {
            _context.Dislikes.Add(Dislike);
            if (_context.SaveChanges() == 1) 
                return true; 
            return false;
        }   

        public bool DeleteDislike(Dislike Dislike)
        {
            _context.Dislikes.Remove(Dislike);
            if (_context.SaveChanges() == 1)
                return true;
            return false;
        }

        public Dislike GetDislike(int DislikeID)
        {
            return _context.Dislikes.Where(dislike => dislike.Id == DislikeID).FirstOrDefault(); 
        }

        public List<Dislike> GetDislikesPost(Post Post)
        {
            return _context.Dislikes.Where(dislike => dislike.PostId == Post.Id).ToList();
        }
    }
}
