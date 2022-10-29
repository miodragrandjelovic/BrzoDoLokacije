namespace PyxisKapriBack.Services.Interfaces
{
    public interface ILikeService
    {
        public List<Like> GetLikes(int lostID, out int numberOfLikes);
        public void AddLike(Post lostID);
        public void DeleteLike(int likeID);
        public void UpdateLike(Like like);

        public int GetNumberOfLikesByLikeID(int likeID);

    }
}
