using PyxisKapriBack.Models;
namespace PyxisKapriBack.Services.Interfaces
{
    public interface ILikeService
    {
        public List<Like> GetLikes(int postID, out int numberOfLikes);
        public Response AddLike(int postID);
        public Response DeleteLike(int postId);

        public int GetNumberOfLikesByPostID(int postID);

        public bool IsLiked(int postID,string username);

    }
}
