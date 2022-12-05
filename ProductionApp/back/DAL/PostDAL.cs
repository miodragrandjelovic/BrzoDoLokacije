using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.DAL
{
    public class PostDAL : IPostDAL
    {
        private readonly Database _context;
        private readonly IUserDAL _iUserDAL; 
        private readonly ILocationDAL locationDAL;
        public PostDAL(Database context, IUserDAL iUserDAL, ILocationDAL ilocationDAL)
        {
            _context = context;
            _iUserDAL = iUserDAL;
            locationDAL = ilocationDAL;
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
            if (user == null)
                throw new Exception(Constants.Constants.resNoFoundUser); 

            return _context.Posts.Where(post => post.UserId == user.Id).Include(post => post.User)
                                                                       .Include(post => post.Dislikes)
                                                                       .Include(post => post.Likes)
                                                                       .Include(post => post.Comments)
                                                                       .Include(post => post.Images)
                                                                       .Include(post => post.Location)
                                                                       .OrderByDescending(post => post.CreatedDate)
                                                                       .ToList(); 
        }

        public List<Post> GetPosts(string username, SortType sortType = SortType.DATE)
        {
            User user = _iUserDAL.GetUser(username);
            if (user == null)
                throw new Exception(Constants.Constants.resNoFoundUser);

            var posts = _context.Posts.Where(post => post.UserId != user.Id).Include(post => post.User)
                                                                       .Include(post => post.Dislikes)
                                                                       .Include(post => post.Likes)
                                                                       .Include(post => post.Comments)
                                                                       .Include(post => post.Images)
                                                                       .Include(post => post.Location);
            return SortListByCriteria(posts, sortType); 
        }

        public List<Post> GetFollowingPosts(string username, SortType sortType = SortType.DATE)
        {
            var _user = _iUserDAL.GetUser(username);
            if (_user == null)
                throw new Exception(Constants.Constants.resNoFoundUser);
            List<int> users = _context.Follow.Where(follow => follow.FollowerId == _user.Id)
                                                          .Select(follow => follow.Following)
                                                          .Select(user => user.Id)
                                                          .ToList();

            IQueryable<Post> posts = _context.Posts.Where(post => users.Contains(post.UserId))
                                                .Include(post => post.User)
                                                .Include(post => post.Dislikes)
                                                .Include(post => post.Likes)
                                                .Include(post => post.Comments)
                                                .Include(post => post.Images)
                                                .Include(post => post.Location); 

            return SortListByCriteria(posts, sortType); 
        }

        public List<Post> GetRecommendedPosts(string username, SortType sortType = SortType.DATE)
        { 
            var _user = _iUserDAL.GetUser(username);
            if (_user == null)
                throw new Exception(Constants.Constants.resNoFoundUser);
            List<int> users = _context.Follow.Where(follow => follow.FollowerId == _user.Id)
                                                          .Select(follow => follow.Following)
                                                          .Select(user => user.Id)
                                                          .ToList();
            users.Add(_user.Id);

            IQueryable<Post> posts = _context.Posts.Where(post => !users.Contains(post.UserId))
                                             .Include(post => post.User)
                                             .Include(post => post.Dislikes)
                                             .Include(post => post.Likes)
                                             .Include(post => post.Comments)
                                             .Include(post => post.Images)
                                             .Include(post => post.Location); 

            return SortListByCriteria(posts, sortType);
        }

        List<Post> SortListByCriteria(IQueryable<Post> orderedQueryable, SortType sortType = SortType.DATE)
        {
            if (sortType == SortType.DATE)
                orderedQueryable = orderedQueryable.OrderByDescending(post => post.CreatedDate);
            else if (sortType == SortType.COUNT_COMMENTS)
                orderedQueryable = orderedQueryable.OrderByDescending(post => post.Comments.Count);
            else if (sortType == SortType.COUNT_LIKES)
                orderedQueryable = orderedQueryable.OrderByDescending(post => post.Likes.Count);

            return orderedQueryable.ToList(); 
        }

        public List<Post> GetPostsBySearch(String search, SortType sortType = SortType.DATE)
        {
            IQueryable<Post> posts; 

            if (!String.IsNullOrEmpty(search))
                posts = _context.Posts.Where(post => post.FullLocation.Contains(search)); 
            else
                posts = _context.Posts;
            posts = posts.Include(post => post.User)
                         .Include(post => post.Dislikes)
                         .Include(post => post.Likes)
                         .Include(post => post.Comments);

            /*List<Location> locations = locationDAL.FilterLocations(search);
            List<int> locationsId = locations.Select(location => location.Id).ToList();

            IQueryable<Post> posts = _context.Posts.Where(post => locationsId.Contains(post.LocationId))
                                             .Include(post => post.User)
                                             .Include(post => post.Dislikes)
                                             .Include(post => post.Likes)
                                             .Include(post => post.Comments)
                                             .Include(post => post.Images)
                                             .Include(post => post.Location)
                                             .Include(post => post.Location.City)
                                             .Include(post => post.Location.City.Country);*/

            return SortListByCriteria(posts, sortType);
        }
    }
}
