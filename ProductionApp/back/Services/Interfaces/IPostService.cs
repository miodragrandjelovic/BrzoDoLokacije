using PyxisKapriBack.Models;
namespace PyxisKapriBack.Services.Interfaces
{
    public interface IPostService
    {
        List<Post> GetUserPosts(string username);
        List<Post> GetPostsForLocation(int LocationID);
        void AddPost(Post post);
        void DeletePost(int postID);
        Post GetPost(int PostID);
        public void SetLikeOnPost(int postID);
    }
}
