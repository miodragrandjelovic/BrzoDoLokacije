using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.Services.Interfaces
{
    public interface IPostService
    {
        List<Post> GetUserPosts(string username);
        List<Post> GetPostsForLocation(int LocationID);
        void AddPost(PostDTO post);
        void DeletePost(int postID);
        Post GetPost(int PostID);
        void SetLikeOnPost(int postID);

        void DeleteUserPost(int postID, int userName);

        void DeletePostById(int postID);
    }
}
