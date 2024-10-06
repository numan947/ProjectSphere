import {
  Text,
  Button,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalHeader,
  ModalOverlay,
  FormControl,
  FormErrorMessage,
  Input,
  HStack,
} from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { Controller, useForm } from "react-hook-form";
import { z } from "zod";

const SingleEmailModalSchema = z.object({
  email: z.string().email("Invalid email address").min(1, "Email is required"),
});
const MultipleEmailModalSchema = z.object({
  email: z
    .string()
    .refine(
      (emailValue) =>
        emailValue
          .split(",")
          .every((item) => z.string().email().safeParse(item).success),
      {
        message: "One or more email addresses are invalid",
      }
    ),
});

const defaultValues = {
  email: "",
};

interface SendCodeModalProps {
  isOpen: boolean;
  onClose: () => void;
  headerText?: string;
  bodyText?: string;
  submitButtonText: string;
  multi?: boolean;
  placeholder?: string;
  onSubmit: (data: any) => void;
}

const EmailAddressModal = ({
  multi = false,
  submitButtonText,
  placeholder,
  isOpen,
  onClose,
  headerText,
  bodyText,
  onSubmit,
}: SendCodeModalProps) => {
  const {
    control,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    resolver: zodResolver(
      multi ? MultipleEmailModalSchema : SingleEmailModalSchema
    ),
    defaultValues: defaultValues,
  });

  const handleOnClose = () => {
    reset();
    onClose();
  };

  const onSubmitRequest = (data: any) => {
    onSubmit && onSubmit(data.email);
    handleOnClose();
  };

  return (
    <>
      <Modal isOpen={isOpen} onClose={handleOnClose}>
        <ModalOverlay />
        <ModalContent>
          {headerText && <ModalHeader>{headerText}</ModalHeader>}
          <ModalCloseButton />
          <ModalBody>
            {bodyText && <Text pb={3}>{bodyText}</Text>}
            <form onSubmit={handleSubmit(onSubmitRequest)}>
              <FormControl isInvalid={!!errors.email}>
                <Controller
                  name="email"
                  control={control}
                  render={({ field }) => (
                    <Input
                      {...field}
                      placeholder={
                        placeholder
                          ? placeholder
                          : "Enter a valid email address"
                      }
                    />
                  )}
                />
                <FormErrorMessage>{errors.email?.message}</FormErrorMessage>
              </FormControl>
              <HStack mt={4} pb={4} justifyContent="center">
                <Button
                  colorScheme="green"
                  mr={3}
                  onClick={handleOnClose}
                  variant="outline"
                >
                  Cancel
                </Button>
                <Button variant="outline" colorScheme="blue" type="submit">
                  {submitButtonText}
                </Button>
              </HStack>
            </form>
          </ModalBody>
        </ModalContent>
      </Modal>
    </>
  );
};

export default EmailAddressModal;
