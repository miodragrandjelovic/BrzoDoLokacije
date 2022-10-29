using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Services
{
    public class PostService : IPostService
    {
        private readonly IPostDAL postDAL;
        private readonly ILikeService likeService;
        private readonly IUserService userService;

        public PostService(IPostDAL postDAL, ILikeService likeService, IUserService userService)
        {
            this.postDAL = postDAL;
            this.likeService = likeService;
            this.userService = userService;
        }
        public void AddPost(Post post)
        {
            postDAL.AddPost(post);
        }

        public void DeletePost(int postID)
        {
            postDAL.DeletePost(postID);
        }

        public void DeletePostById(int postID)
        {
            throw new NotImplementedException();
        }

        public void DeleteUserPost(int postID, int userName)
        {
            throw new NotImplementedException();
        }

        public Post GetPost(int PostID)
        {
            return postDAL.GetPost(PostID);
        }

        public List<Post> GetPostsForLocation(int LocationID)
        {
            return postDAL.GetPostsForLocation(LocationID);
        }

        public List<Post> GetUserPosts(string username)
        {
            return postDAL.GetUserPosts(username);
        }


        public void SetLikeOnPost(int postID)
        {
            Post post = GetPost(postID);
            likeService.AddLike(post, userService.GetLoggedUser());

        }
    }
}
