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

        public void DeletePost(Post post)
        {
            postDAL.DeletePost(post);
        }

        public Post GetPost(int PostID)
        {
            return postDAL.GetPost(PostID);
        }

        public List<Post> GetPostsForLocation(int LocationID)
        {
            return postDAL.GetPostsForLocation(LocationID);
        }

        public List<Post> GetUserPosts(int UserID)
        {
            return postDAL.GetUserPosts(UserID);
        }
    }
}
