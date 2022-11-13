using PyxisKapriBack.Models;
namespace PyxisKapriBack.DAL.Interfaces
{
    public interface IPostDAL
    {
        void AddPost(Post post);
        bool DeletePost(int PostID);
        void UpdatePost(Post post);
        Post GetPost(int PostID);
        List<Post> GetUserPosts(String username);
        List<Post> GetPostsForLocation(int LocationID);

        List<Post> GetPosts(String username);   
    }
}
