import {
  FormControl,
  FormErrorMessage,
  FormLabel,
} from "@chakra-ui/form-control";
import { Input } from "@chakra-ui/input";
import { Textarea } from "@chakra-ui/textarea";
import { zodResolver } from "@hookform/resolvers/zod";
import { Controller, useForm } from "react-hook-form";
import { z } from "zod";
import { CreatableSelect, GroupBase, Select } from "chakra-react-select";
import { Button, Flex, HStack, Spinner } from "@chakra-ui/react";
import { FaPlus } from "react-icons/fa";
import { useCategories, useTags } from "../hooks/useMetadata";
import { useCreateProject } from "../hooks/useProjects";

const ProjectFormSchema = z.object({
  name: z.string().min(2).max(255),
  description: z.string().max(1000),
  categories: z.array(z.string().min(2).max(255)),
  tags: z.array(z.string().min(2).max(255)),
});

const defaultValues = {
  name: "",
  description: "",
  categories: [],
  tags: [],
};

interface OptionGroup {
  label: string;
  value: string;
}

interface ProjectFormProps {
  onCancelled: () => void;
  onCreatedSuccess: () => void;
  onCreatedError: (msg: string) => void;
}

type ProjectFormValues = z.infer<typeof ProjectFormSchema>;

const CreateProjectForm = ({
  onCancelled,
  onCreatedError,
  onCreatedSuccess,
}: ProjectFormProps) => {
  const { data: categories, refetch: refetchCategories } = useCategories();
  const { data: tags, refetch: refetchTags } = useTags();

  const {
    mutate: createProject,
    isPending: createProjectPending,
    isSuccess,
  } = useCreateProject(onCreatedSuccess, onCreatedError);

  const {
    control,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<ProjectFormValues>({
    resolver: zodResolver(ProjectFormSchema),
    defaultValues: defaultValues,
  });

  const onFormSubmit = (data: ProjectFormValues) => {
    createProject(data);
  };

  if (createProjectPending) {
    return (
      <Flex justifyContent="center">
        <Spinner />
      </Flex>
    );
  }

  return (
    <form onSubmit={handleSubmit(onFormSubmit)}>
      <FormControl isInvalid={!!errors.name}>
        <FormLabel>Name</FormLabel>
        <Controller
          name="name"
          control={control}
          render={({ field }) => (
            <Input {...field} type="text" placeholder="Name of the project" />
          )}
        />
        <FormErrorMessage>{errors.name?.message}</FormErrorMessage>
      </FormControl>

      <FormControl isInvalid={!!errors.description} mt={2}>
        <FormLabel>Description</FormLabel>
        <Controller
          name="description"
          control={control}
          render={({ field }) => (
            <Textarea {...field} placeholder="Description of the project" />
          )}
        />
        <FormErrorMessage>{errors.description?.message}</FormErrorMessage>
      </FormControl>

      <FormControl isInvalid={!!errors.categories} mt={2}>
        <FormLabel>Categories</FormLabel>
        <Controller
          name="categories"
          control={control}
          render={({ field }) => (
            <CreatableSelect<OptionGroup, true, GroupBase<OptionGroup>>
              {...field}
              options={(categories || []).map(
                (category) =>
                  ({
                    label: category,
                    value: category,
                  } as OptionGroup)
              )}
              value={field.value.map((val) => ({ label: val, value: val }))}
              onChange={(selectedOptions) =>
                field.onChange(selectedOptions.map((option) => option.value))
              }
              placeholder="Select categories"
              colorScheme="purple"
              isMulti
              closeMenuOnSelect={false}
            />
          )}
        />
        <FormErrorMessage>{errors.categories?.message}</FormErrorMessage>
      </FormControl>

      <FormControl isInvalid={!!errors.tags} mt={2}>
        <FormLabel>Tags</FormLabel>
        <Controller
          name="tags"
          control={control}
          render={({ field }) => (
            <CreatableSelect<OptionGroup, true, GroupBase<OptionGroup>>
              {...field}
              options={(tags || []).map(
                (tag) =>
                  ({
                    label: tag,
                    value: tag,
                  } as OptionGroup)
              )}
              value={field.value.map((val) => ({ label: val, value: val }))}
              onChange={(selectedOptions) =>
                field.onChange(selectedOptions.map((option) => option.value))
              }
              colorScheme="blue"
              placeholder="Select Tags"
              isMulti
              closeMenuOnSelect={false}
            />
          )}
        />
        <FormErrorMessage>{errors.tags?.message}</FormErrorMessage>
      </FormControl>
      <HStack mt={4} justifyContent="end">
        <Button
          colorScheme="green"
          type="submit"
          variant="outline"
          isDisabled={createProjectPending}
          leftIcon={<FaPlus />}
        >
          Create
        </Button>
        <Button
          colorScheme="red"
          variant="outline"
          onClick={() => {
            reset(defaultValues);
            onCancelled();
          }}
          isDisabled={createProjectPending}
        >
          Cancel
        </Button>
      </HStack>
    </form>
  );
};

export default CreateProjectForm;
