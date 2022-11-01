using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace PyxisKapriBack.Migrations
{
    public partial class AddedCoverImageProperty : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "ImagePath",
                table: "Posts",
                newName: "CoverImage");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "CoverImage",
                table: "Posts",
                newName: "ImagePath");
        }
    }
}
