using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services.Interfaces
{
    public interface ICommentDislikeService
    {
        public Response IsCommentDisliked(int commentID, string username);
        public Response AddDislikeOnComment(CommentDislike dislike);
        public Response DeleteDislikeFromComment(int dislikeID);
        public List<CommentDislike> GetDislikesOfComment(int commentID);
        public List<User> GetUsersWhoDisliked(int commentID);
    }
}
