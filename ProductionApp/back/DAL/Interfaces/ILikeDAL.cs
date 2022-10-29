namespace PyxisKapriBack.DAL.Interfaces
{
    public interface ILikeDAL
    {
        public List<Like> GetLikes(int PostID , out int NumberOfLikes);
        public void AddLike(Post post, User user);
        public void DeleteLike(int LikeID); 
        public void UpdateLike(Like Like);
    }
}
