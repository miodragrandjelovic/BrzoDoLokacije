using PyxisKapriBack.Models;
namespace PyxisKapriBack.DAL.Interfaces
{
    public interface ILikeDAL
    {
        public List<Like> GetLikes(int PostId , out int NumberOfLikes);
        public bool AddLike(int postId, String username);
        public bool AddLike(Like like); 
        public bool DeleteLike(int postId, string username); 
        public bool IsPostLiked(int postId, string username);
        public Like GetLike(int postId, string username);
        public bool UpdateLike(Like like); 
    }
}
