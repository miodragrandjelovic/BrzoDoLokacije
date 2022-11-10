using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL
{
    public class DislikeDAL : IDislikeDAL
    {
        private Database _context; 
        public DislikeDAL(Database context)
        {
            _context = context;
        }

        public bool AddDislike(Dislike Dislike)
        {
            if (Dislike == null)
                return false; 
            _context.Dislikes.Add(Dislike);
            _context.SaveChanges();
            return true;
        }   

        public bool DeleteDislike(int DislikeID)
        {
            Dislike dislike = GetDislike(DislikeID); 
            if(dislike == null) 
                return false;
            _context.Dislikes.Remove(dislike);
            _context.SaveChanges();
            return true; 
        }

        public Dislike GetDislike(int DislikeID)
        {
            return _context.Dislikes.Where(dislike => dislike.Id == DislikeID).Include(dislike => dislike.User)
                                                                              .Include(dislike => dislike.Post)
                                                                              .FirstOrDefault(); 
        }

        public List<Dislike> GetDislikesPost(Post Post)
        {
            return _context.Dislikes.Where(dislike => dislike.PostId == Post.Id).Include(dislike => dislike.User)
                                                                                .Include(dislike => dislike.Post)
                                                                                .ToList();
        }
    }
}
