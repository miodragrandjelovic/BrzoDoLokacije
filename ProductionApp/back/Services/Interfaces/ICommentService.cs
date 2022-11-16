using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services.Interfaces
{
    public interface ICommentService
    {
        public Response AddComment(NewCommentDTO comment);
        public Response DeleteComment(int commentId);

        public Comment GetComment(int commentId);
        public List<Comment> GetCommentsPost(int postId);
    }
}
