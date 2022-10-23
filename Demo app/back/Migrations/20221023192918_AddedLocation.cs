using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace PyxisKapriBack.Migrations
{
    public partial class AddedLocation : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Posts_Cities_CityId",
                table: "Posts");

            migrationBuilder.RenameColumn(
                name: "CityId",
                table: "Posts",
                newName: "LocationId");

            migrationBuilder.RenameIndex(
                name: "IX_Posts_CityId",
                table: "Posts",
                newName: "IX_Posts_LocationId");

            migrationBuilder.CreateTable(
                name: "Locations",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    Name = table.Column<string>(type: "varchar(100)", maxLength: 100, nullable: false)
                        .Annotation("MySql:CharSet", "utf8mb4"),
                    CityID = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Locations", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Locations_Cities_CityID",
                        column: x => x.CityID,
                        principalTable: "Cities",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                })
                .Annotation("MySql:CharSet", "utf8mb4");

            migrationBuilder.CreateIndex(
                name: "IX_Locations_CityID",
                table: "Locations",
                column: "CityID");

            migrationBuilder.AddForeignKey(
                name: "FK_Posts_Locations_LocationId",
                table: "Posts",
                column: "LocationId",
                principalTable: "Locations",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Posts_Locations_LocationId",
                table: "Posts");

            migrationBuilder.DropTable(
                name: "Locations");

            migrationBuilder.RenameColumn(
                name: "LocationId",
                table: "Posts",
                newName: "CityId");

            migrationBuilder.RenameIndex(
                name: "IX_Posts_LocationId",
                table: "Posts",
                newName: "IX_Posts_CityId");

            migrationBuilder.AddForeignKey(
                name: "FK_Posts_Cities_CityId",
                table: "Posts",
                column: "CityId",
                principalTable: "Cities",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
