using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.DAL
{
    public class PostDAL : IPostDAL
    {
        private readonly Database _context;
        private readonly IUserDAL _iUserDAL; 
        public PostDAL(Database context, IUserDAL iUserDAL)
        {
            _context = context;
            _iUserDAL = iUserDAL;
        }
        public void AddPost(Post post)
        {
            _context.Posts.Add(post);
            _context.SaveChanges(); 
        }

        public bool DeletePost(int PostID)
        {
            Post post = GetPost(PostID);

            if (post == null)
                return false; 

            _context.Posts.Remove(post);
            _context.SaveChanges();
            return true; 
        }

        public Post GetPost(int PostID)
        {
            return _context.Posts.Where(post => post.Id == PostID).Include(post => post.User)
                                                                  .Include(post => post.Dislikes)
                                                                  .Include(post => post.Likes)
                                                                  .Include(post => post.Comments)
                                                                  .Include(post => post.Images)
                                                                  .Include(post => post.Location)
                                                                  .OrderByDescending(post => post.CreatedDate)
                                                                  .FirstOrDefault(); 
        }

        public void UpdatePost(Post post)
        {
            _context.Posts.Update(post);
            _context.SaveChanges();
        }

        public List<Post> GetPostsForLocation(int LocationID)
        {
            return _context.Posts.Where(post => post.LocationId == LocationID).Include(post => post.User)
                                                                              .Include(post => post.Dislikes)
                                                                              .Include(post => post.Likes)
                                                                              .Include(post => post.Comments)
                                                                              .Include(post => post.Images)
                                                                              .Include(post => post.Location)
                                                                              .OrderByDescending(post => post.CreatedDate)
                                                                              .ToList(); 
        }

        public List<Post> GetUserPosts(string username)
        {
            User user = _iUserDAL.GetUser(username); 
            return _context.Posts.Where(post => post.UserId == user.Id).Include(post => post.User)
                                                                       .Include(post => post.Dislikes)
                                                                       .Include(post => post.Likes)
                                                                       .Include(post => post.Comments)
                                                                       .Include(post => post.Images)
                                                                       .Include(post => post.Location)
                                                                       .OrderByDescending(post => post.CreatedDate)
                                                                       .ToList(); 
        }

        public List<Post> GetPosts(string username)
        {
            User user = _iUserDAL.GetUser(username);
            if (user == null)
                return null; 
            return _context.Posts.Where(post => post.UserId != user.Id).Include(post => post.User)
                                                                       .Include(post => post.Dislikes)
                                                                       .Include(post => post.Likes)
                                                                       .Include(post => post.Comments)
                                                                       .Include(post => post.Images)
                                                                       .Include(post => post.Location)
                                                                       .OrderByDescending(post => post.CreatedDate)
                                                                       .ToList();
        }

        public List<Post> GetFollowingPosts(string username)
        {
            var _user = _iUserDAL.GetUser(username);
            if (_user == null)
                return null;
            List<int> users = _context.Follow.Where(follow => follow.FollowerId == _user.Id)
                                                          .Select(follow => follow.Following)
                                                          .Select(user => user.Id)
                                                          .ToList(); 

            List<Post> posts = _context.Posts.Where(post => users.Contains(post.UserId))
                                             .Include(post => post.User)
                                             .Include(post => post.Dislikes)
                                             .Include(post => post.Likes)
                                             .Include(post => post.Comments)
                                             .Include(post => post.Images)
                                             .Include(post => post.Location)
                                             .OrderByDescending(post => post.CreatedDate)
                                             .ToList();
            return posts; 
        }

        public List<Post> GetRecommendedPosts(string username)
        {
            var _user = _iUserDAL.GetUser(username);
            if (_user == null)
                return null;
            List<int> users = _context.Follow.Where(follow => follow.FollowerId == _user.Id)
                                                          .Select(follow => follow.Following)
                                                          .Select(user => user.Id)
                                                          .ToList();
            users.Add(_user.Id); 
            List<Post> posts = _context.Posts.Where(post => !users.Contains(post.UserId))
                                             .Include(post => post.User)
                                             .Include(post => post.Dislikes)
                                             .Include(post => post.Likes)
                                             .Include(post => post.Comments)
                                             .Include(post => post.Images)
                                             .Include(post => post.Location)
                                             .OrderByDescending(post => post.CreatedDate)
                                             .ToList();
            return posts;
        }
    }
}
