import { SimpleGrid, Skeleton } from "@chakra-ui/react";
import ProjectCard from "./ProjectCard";
import { ProjectShortResponse } from "../Entities";

interface ProjectCardListProps {
  projects: ProjectShortResponse[];
  isLoading: boolean;
}

const ProjectCardList = ({ projects, isLoading }: ProjectCardListProps) => {
  return (
    <>
      <SimpleGrid columns={{ sm: 1, md: 2, lg: 3 }} spacing={5}>
        {projects.map((project) => (
          <Skeleton isLoaded={!isLoading} key={project.id}>
            <ProjectCard key={project.id} project={project} />
          </Skeleton>
        ))}
      </SimpleGrid>
    </>
  );
};

export default ProjectCardList;
