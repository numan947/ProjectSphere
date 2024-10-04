import { SimpleGrid } from "@chakra-ui/react";
import ProjectCard from "../building-blocks/ProjectCard";
import { ProjectShortResponse } from "../domain/projects/Entities";

const ProjectCardList = () => {
  const project: ProjectShortResponse = {
    id: 12345,
    name: "Project Alpha",
    description:
      "This project focuses on developing a new feature for our application.",
    category: "Development",
    memberCount: 5,
    issueCount: 12,
    tags: ["feature", "development", "app"],
  };
  return (
    <>
      <SimpleGrid columns={{ sm: 1, md: 2, lg: 3 }} spacing={5}>
        <ProjectCard project={project} />
        <ProjectCard project={project} />
        <ProjectCard project={project} />
      </SimpleGrid>
    </>
  );
};

export default ProjectCardList;
