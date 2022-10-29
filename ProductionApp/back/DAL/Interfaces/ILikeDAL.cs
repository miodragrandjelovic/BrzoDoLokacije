namespace PyxisKapriBack.DAL.Interfaces
{
    public interface ILikeDAL
    {
        public List<Like> GetLikes(int PostID , out int NumberOfLikes);
        public void AddLike(Post post, String username);
        public void DeleteLike(int LikeID); 
    }
}
