using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.Services.Interfaces
{
    public interface IPostService
    {
        List<PostDTO> GetUserPosts(string username);
        List<Post> GetPostsForLocation(int LocationID);
        void AddPost(NewPostDTO post);
        void DeletePost(int postID);
        Post GetPost(int PostID);
        void SetLikeOnPost(int postID);

        bool DeleteUserPost(int postID, string userName);

    }
}
