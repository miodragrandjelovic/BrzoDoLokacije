using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.Services.Interfaces
{
    public interface IPostService
    {
        List<Post> GetUserPosts(string username);
        List<Post> GetPostsForLocation(int LocationID);
        void AddPost(NewPostDTO post);
        Response DeletePost(int postID);
        Post GetPost(int PostID);
        void SetLikeOnPost(int postID);

        Response DeleteUserPost(int postID);

    }
}
