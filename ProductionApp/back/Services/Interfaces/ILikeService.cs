namespace PyxisKapriBack.Services.Interfaces
{
    public interface ILikeService
    {
        public List<Like> GetLikes(int lostID, out int numberOfLikes);
        public void AddLike(Post post, String username);
        public void DeleteLike(int likeID);

        public int GetNumberOfLikesByLikeID(int likeID);

    }
}
