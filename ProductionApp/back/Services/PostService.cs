using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Services
{
    public class PostService : IPostService
    {
        private readonly IPostDAL postDAL;

        public PostService(IPostDAL postDAL)
        {
            this.postDAL = postDAL;
        }
        public void AddPost(Post post)
        {
            postDAL.AddPost(post);
        }

        public void DeletePost(int postID)
        {
            postDAL.DeletePost(postID);
        }

        public Post GetPost(string username)
        {
            return postDAL.GetPost(username);
        }

        public List<Post> GetPostsForLocation(int LocationID)
        {
            return postDAL.GetPostsForLocation(LocationID);
        }

        public List<Post> GetUserPosts(string username)
        {
            return postDAL.GetUserPosts(username);
        }
    }
}
