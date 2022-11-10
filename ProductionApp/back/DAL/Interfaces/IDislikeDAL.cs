using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface IDislikeDAL
    {
        bool AddDislike(Dislike Dislike); 
        bool DeleteDislike(int DislikeID);
        
        Dislike GetDislike(int DislikeID);

        List<Dislike> GetDislikesPost(Post Post); 
    }
}
