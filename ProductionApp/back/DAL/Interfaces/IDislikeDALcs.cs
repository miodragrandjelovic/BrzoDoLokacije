using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface IDislikeDALcs
    {
        bool AddDislike(Dislike Dislike); 
        bool DeleteDislike(Dislike Dislike);
        
        Dislike GetDislike(int DislikeID);

        List<Dislike> GetDislikesPost(Post Post); 
    }
}
