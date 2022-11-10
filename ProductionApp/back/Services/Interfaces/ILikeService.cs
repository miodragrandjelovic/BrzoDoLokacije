using PyxisKapriBack.Models;
namespace PyxisKapriBack.Services.Interfaces
{
    public interface ILikeService
    {
        public List<Like> GetLikes(int postID, out int numberOfLikes);
        public Response AddLike(int postId, String username);
        public void DeleteLike(int likeID);

        public int GetNumberOfLikesByPostID(int postID);

    }
}
