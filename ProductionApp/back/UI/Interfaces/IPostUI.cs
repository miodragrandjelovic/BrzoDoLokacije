using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.UI.Interfaces
{
    public interface IPostUI
    {
        List<PostDTO> GetUserPosts(string username);
        List<PostDTO> GetPostsForLocation(int LocationID);
        void AddPost(NewPostDTO post);
        Response DeletePost(int postID);
        Post GetPost(int PostID);
        void SetLikeOnPost(int postID);

        Response DeleteUserPost(int postID);
    }
}
